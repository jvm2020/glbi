// GLBI Data - Based on PBO Report February 2025
// Federal Rates for GLBI

const GLBI_DATA = {
    // Federal Base Amounts (Annual)
    federalRates: {
        single: 20000,
        couple: 35000,
        singleParent1: 28000,
        singleParent2: 32000,
        couple1: 42000,
        couple2: 48000
    },

    // Disability Amount (Annual) - Not clawed back
    disabilityAmount: 6000,

    // Provincial and Federal Support Comparison (Annual estimates)
    // These represent typical amounts from current provincial assistance and federal tax credits
    provincialSupport: {
        AB: {
            name: 'Alberta',
            single: 10800,
            couple: 16200,
            singleParent1: 18600,
            singleParent2: 21000,
            couple1: 21000,
            couple2: 24000,
            programs: ['AISH (Assured Income for the Severely Handicapped)', 'Income Support', 'GST Credit', 'Canada Child Benefit']
        },
        BC: {
            name: 'British Columbia',
            single: 12600,
            couple: 19800,
            singleParent1: 21000,
            singleParent2: 24000,
            couple1: 26400,
            couple2: 30000,
            programs: ['BC Income Assistance', 'Disability Assistance', 'GST Credit', 'Climate Action Tax Credit', 'Canada Child Benefit']
        },
        MB: {
            name: 'Manitoba',
            single: 10200,
            couple: 15600,
            singleParent1: 18000,
            singleParent2: 20400,
            couple1: 21600,
            couple2: 24600,
            programs: ['Employment and Income Assistance', 'GST Credit', 'Canada Child Benefit']
        },
        NB: {
            name: 'New Brunswick',
            single: 9000,
            couple: 13800,
            singleParent1: 16800,
            singleParent2: 19200,
            couple1: 20400,
            couple2: 23400,
            programs: ['Social Development', 'GST Credit', 'New Brunswick Child Tax Benefit', 'Canada Child Benefit']
        },
        NL: {
            name: 'Newfoundland and Labrador',
            single: 10800,
            couple: 16800,
            singleParent1: 19200,
            singleParent2: 21600,
            couple1: 22800,
            couple2: 26400,
            programs: ['Income Support', 'GST Credit', 'Newfoundland and Labrador Child Benefit', 'Canada Child Benefit']
        },
        NT: {
            name: 'Northwest Territories',
            single: 16800,
            couple: 26400,
            singleParent1: 28800,
            singleParent2: 32400,
            couple1: 34800,
            couple2: 39600,
            programs: ['Income Assistance', 'GST Credit', 'Canada Child Benefit', 'Northern Residents Deductions']
        },
        NS: {
            name: 'Nova Scotia',
            single: 9600,
            couple: 14400,
            singleParent1: 17400,
            singleParent2: 19800,
            couple1: 21000,
            couple2: 24000,
            programs: ['Income Assistance', 'GST Credit', 'Nova Scotia Child Benefit', 'Canada Child Benefit']
        },
        NU: {
            name: 'Nunavut',
            single: 18000,
            couple: 28800,
            singleParent1: 31200,
            singleParent2: 35400,
            couple1: 37800,
            couple2: 43200,
            programs: ['Income Assistance', 'GST Credit', 'Canada Child Benefit', 'Northern Residents Deductions']
        },
        ON: {
            name: 'Ontario',
            single: 10800,
            couple: 16800,
            singleParent1: 19800,
            singleParent2: 22800,
            couple1: 24000,
            couple2: 27600,
            programs: ['Ontario Works', 'ODSP (Ontario Disability Support Program)', 'GST Credit', 'Ontario Trillium Benefit', 'Canada Child Benefit']
        },
        PE: {
            name: 'Prince Edward Island',
            single: 9600,
            couple: 14400,
            singleParent1: 17400,
            singleParent2: 19800,
            couple1: 21000,
            couple2: 24000,
            programs: ['Social Assistance', 'GST Credit', 'Canada Child Benefit']
        },
        QC: {
            name: 'Quebec',
            single: 11400,
            couple: 17400,
            singleParent1: 20400,
            singleParent2: 23400,
            couple1: 25200,
            couple2: 28800,
            programs: ['Social Solidarity Program', 'Social Assistance Program', 'GST Credit', 'Quebec Family Allowance', 'Canada Child Benefit']
        },
        SK: {
            name: 'Saskatchewan',
            single: 10200,
            couple: 15600,
            singleParent1: 18000,
            singleParent2: 20400,
            couple1: 21600,
            couple2: 24600,
            programs: ['Saskatchewan Assured Income for Disability', 'Saskatchewan Assistance Program', 'GST Credit', 'Canada Child Benefit']
        },
        YT: {
            name: 'Yukon',
            single: 15600,
            couple: 24000,
            singleParent1: 27000,
            singleParent2: 30600,
            couple1: 33000,
            couple2: 37800,
            programs: ['Social Assistance', 'GST Credit', 'Canada Child Benefit', 'Northern Residents Deductions']
        }
    },

    // Household type labels
    householdLabels: {
        single: 'Single Adult',
        couple: 'Couple',
        singleParent1: 'Single Parent (1 child)',
        singleParent2: 'Single Parent (2+ children)',
        couple1: 'Couple (1 child)',
        couple2: 'Couple (2+ children)'
    },

    // Reduction rate descriptions
    reductionRateDescriptions: {
        50: 'For every dollar you earn, your GLBI benefit is reduced by 50 cents. This moderate clawback rate balances work incentives with program costs.',
        25: 'For every dollar you earn, your GLBI benefit is reduced by 25 cents. This low clawback rate provides strong work incentives while maintaining basic income support.',
        15: 'For every dollar you earn, your GLBI benefit is reduced by 15 cents. This minimal clawback rate maximizes work incentives and allows recipients to keep most of their earned income alongside GLBI support.'
    }
};

// Utility function to format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-CA', {
        style: 'currency',
        currency: 'CAD',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(amount);
}

// Utility function to format currency with decimals
function formatCurrencyDecimal(amount) {
    return new Intl.NumberFormat('en-CA', {
        style: 'currency',
        currency: 'CAD',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount);
}
